package it.telami.commons.unsafe;

public final class Gate {
    public void lock () {}

    public void forceLock () {}

    public boolean tryLock () {
        return false;
    }

    public void unlock () {}

    public boolean tryWait () {
        return false;
    }
    public boolean isLocked () {
        return false;
    }
}
