package proteinhp;

public final class ObjectPool{

    private Object pool[];
    private int max;
    private int last;
    private int current = -1;
    private Object lock;
    public static final int DEFAULT_SIZE = 32;
    static final int debug = 0;



    public ObjectPool(){
        this(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public ObjectPool(int size){
        this(size, size);
    }

    public ObjectPool(int size, int max){
        this.max = max;
        pool = new Object[size];
        this.last = size - 1;
        lock = new Object();
    }



    public void set(Object o){
        put(o);
    }


    //Add object to pool, silent nothing if pool full

    public void put(Object o){
        synchronized(lock){
            if (current < last){
                current++;
                pool[current] = o;
            }
            else if (current < max){
                //relocate
                int newSize = pool.length * 2;
                if (newSize > max)
                    newSize = max + 1;
                Object tmp[] = new Object[newSize];
                last = newSize - 1;
                System.arraycopy(pool, 0, tmp, 0, pool.length);
                pool = tmp;
                current++;
                pool[current] = o;
            }
        }
    }
    
    //get object from pool, null of pool is empty
    public Object get(){
        Object item = null;
        synchronized(lock){
            if (current >= 0){
                item = pool[current];
                pool[current] = null;
                current -= 1;
            }
        }
        return item;
    }

    //size of the pool
    public int getMax(){
        return max;
    }
    
    //number of elements in the pool
    public int getCount(){
        return current + 1;
    }

    public void shutdown(){


    }

}
