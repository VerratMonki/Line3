package interfaces;

public interface LineConsumer {

    void nextLine(String line);

    default void beforeReadFile(String fileName){}

    default void afterReadFile(String fileName){}

    default boolean isBreak()
    {
        return false;
    }
}