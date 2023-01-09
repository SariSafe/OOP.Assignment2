package Ex2_2;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomExecutor
{
    private ThreadPoolExecutor threadPool;
    private AtomicInteger currentMax;

    /**
     * function C'TOR.
     * init the threadPool as explain us in lecture 9,
     * with the correct values was needed for this assignment.
     */
    CustomExecutor()
    {
        this.currentMax = new AtomicInteger(2000);
        int numOfCores = Runtime.getRuntime().availableProcessors();
        this.threadPool = new ThreadPoolExecutor(numOfCores/2,
                numOfCores-1,
                300,
                TimeUnit.MILLISECONDS,
                new ourPriorityBlockingQueue(this.currentMax));//new PriorityBlockingQueue<>()); // LinkedBlockingQueue<>()
        //this.currentMax = Integer.MAX_VALUE;
    }


    /**
     * function submit new Task to the tasks queue
     * @param task type Task
     * @return the correct Future type according to Task
     * @param <T> the return value from call function of Task.
     */
    public <T> Future<T> submit(Task<T> task)
    {
        // check if priority of current Task to submit can replace
        // the current max value. (max - important. so lowers value are good).
//        if(task.getPriority().getPriorityValue() < this.currentMax)
//        {
//            this.currentMax = task.getPriority().getPriorityValue();
//        }

        // submit the Task to the 'real' submit of threadPool.
        return this.threadPool.submit(task);
    }


    /**
     * function submit new Task to the tasks queue
     * @param c type Callable for making new Task object
     * @param tt type TaskType for making new Task object
     * @return the correct Future type according to Task
     * @param <T> the return value from call function of Task.
     */
    public <T> Future<T> submit(Callable<T> c, TaskType tt)
    {
        return submit(Task.createTask(c, tt));
    }


    /**
     * function submit new Task to the tasks queue
     * @param c type Callable for making new Task object
     * @return the correct Future type according to Task
     * @param <T> the return value from call function of Task.
     */
    public <T> Future<T> submit(Callable<T> c)
    {
        return submit(Task.createTask(c));
    }


    // TODO: public int getCurrentMax() better!
    /**
     * function getter
     * @return the current max
     * (lower value is higher priority)
     */
    public AtomicInteger getCurrentMax()
    {
        return this.currentMax;
    }


    /**
     * function make shutdown to threadPool member.
     */
    public void gracefullyTerminate()
    {
        this.threadPool.shutdown();
    }


    /**
     * function give users option to exam if two CustomExecutors are the same.
     * @param o is the other object to exam.
     * @return True for equal, False for not.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CustomExecutor that)) return false;
        return threadPool.equals(that.threadPool);
    }


    /**
     * function create Hash value for CustomExecutor object.
     * @return the integer Hash value.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(threadPool);
    }


    /**
     * override toString of this object
     * @return String represent the CustomExecutor object.
     */
    @Override
    public String toString()
    {
        return "CustomExecutor{" +
                "threadPool=" + threadPool +
                '}';
    }

    /**
    * Returns true if this executor has been shut down
    * */
    public boolean getisShutDown()
    {
        return threadPool.isShutdown();
    }
}
