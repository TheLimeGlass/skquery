package com.w00tmast3r.skquery.skript;

public class DummyClasses {

    public static abstract class DummyBase {
        protected String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DummyBase)) return false;
            DummyBase dummyBase = (DummyBase) o;
            return value.equals(dummyBase.value);

        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }
    }

    public static class _1 extends DummyBase {
    }

    public static class _2 extends DummyBase {
    }

    public static class _3 extends DummyBase {
    }

    public static class _4 extends DummyBase {
    }

    public static class _5 extends DummyBase {
    }

    public static class _6 extends DummyBase {
    }

    public static class _7 extends DummyBase {
    }

    public static class _8 extends DummyBase {
    }

    public static class _9 extends DummyBase {
    }

    public static class _10 extends DummyBase {
    }

    public static class _11 extends DummyBase {
    }

    public static class _12 extends DummyBase {
    }

    public static class _13 extends DummyBase {
    }

    public static class _14 extends DummyBase {
    }

    public static class _15 extends DummyBase {
    }

    public static class _16 extends DummyBase {
    }

    public static class _17 extends DummyBase {
    }

    public static class _18 extends DummyBase {
    }

    public static class _19 extends DummyBase {
    }

    public static class _20 extends DummyBase {
    }

    public static class _21 extends DummyBase {
    }

    public static class _22 extends DummyBase {
    }

    public static class _23 extends DummyBase {
    }

    public static class _24 extends DummyBase {
    }

    public static class _25 extends DummyBase {
    }

    public static class _26 extends DummyBase {
    }

    public static class _27 extends DummyBase {
    }

    public static class _28 extends DummyBase {
    }

    public static class _29 extends DummyBase {
    }

    public static class _30 extends DummyBase {
    }

}
