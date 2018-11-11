package com.w00tmast3r.skquery.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import org.eclipse.jdt.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public class EnumClassInfo<E extends Enum<E>> {

    private final Class<E> enumType;
    private final ClassInfo<E> classInfo;
    private final String codeName;
    private final String user;
    private final HashMap<String, String> synonyms = new HashMap<>();

    private EnumClassInfo(Class<E> enumType, String codeName, @Nullable String user) {
        this.enumType = enumType;
        this.codeName = codeName;
        this.user = user;
        classInfo = new ClassInfo<>(enumType, codeName);
    }

    public static <E extends Enum<E>> EnumClassInfo<E> create(Class<E> enumType, String codeName, @Nullable String user) {
        if (user == null) user = codeName + "s?";
        return new EnumClassInfo<>(enumType, codeName, user);
    }

    public static <E extends Enum<E>> EnumClassInfo<E> create(Class<E> enumType, String codeName) {
        return new EnumClassInfo<>(enumType, codeName, codeName + "s?");
    }

    public EnumClassInfo<E> addSynonym(String regex, String actualValue) {
        synonyms.put(regex, actualValue);
        return this;
    }

    public EnumClassInfo<E> after(String... after) {
        classInfo.after(after);
        return this;
    }

    public EnumClassInfo<E> before(String... before) {
        classInfo.before(before);
        return this;
    }

    public ClassInfo<E> register() {
        if (Classes.getExactClassInfo(enumType) == null) {
            //for .lang files in the future
            //Classes.registerClass(classInfo.user(user).parser(new LangEnumParser<E>(codeName, enumType)).serializer(new EnumSerializer<>(enumType)));
            Classes.registerClass(classInfo.user(user).parser(new Parser<E>() {
                @Override
                public E parse(String s, ParseContext parseContext) {
                    if (s.startsWith(codeName + ":")) {
                        s = s.substring(codeName.length() + 1, s.length());
                    }
                    try {
                        for (Map.Entry<String, String> p : synonyms.entrySet()) {
                            if (s.matches(p.getKey())) {
                                return E.valueOf(enumType, p.getValue());
                            }
                        }
                        return E.valueOf(enumType, s.replace(" ", "_").toUpperCase().trim());
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                }

                @Override
                public String toString(E e, int i) {
                    return e.toString();
                }

                @Override
                public String toVariableNameString(E e) {
                    return codeName + ':' + e.toString();
                }

                @Override
                public String getVariableNamePattern() {
                    return codeName + ":.+";
                }
            }).serializer(new EnumSerializer<>(enumType)));
        }
        return classInfo;
    }

    public ClassInfo<E> getClassInfo() {
        return classInfo;
    }
}