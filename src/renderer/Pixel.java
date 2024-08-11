package renderer;


/**
 * The Pixel class is a helper class used for multi-threading in the renderer and for tracking its progress.
 * It consists of a main follow-up object and several secondary objects, with one object per thread.
 *
 * The class provides functionality for allocating and tracking pixels, as well as printing progress updates.
 * It is designed to be thread-safe.
 *
 * @author Dan
 */
class Pixel {
    private static int maxRows = 0;
    private static int maxCols = 0;
    private static long totalPixels = 0L;

    private static volatile int cRow = 0;
    private static volatile int cCol = -1;
    private static volatile long pixels = 0L;
    private static volatile long last = -1L;
    private static volatile int lastPrinted = -1;

    private static boolean print = false;
    private static long printInterval = 100L;
    private static final String PRINT_FORMAT = "%5.1f%%\r";
    private static Object mutexNext = new Object();
    private static Object mutexPixels = new Object();

    int row;
    int col;

    /**
     * Initializes the pixel data for multi-threading.
     *
     * @param maxRows   the number of pixel rows
     * @param maxCols   the number of pixel columns
     * @param interval  the print time interval in seconds, 0 if printing is not required
     */
    static void initialize(int maxRows, int maxCols, double interval) {
        Pixel.maxRows = maxRows;
        Pixel.maxCols = maxCols;
        Pixel.totalPixels = (long) maxRows * maxCols;
        cRow = 0;
        cCol = -1;
        pixels = 0;
        printInterval = (long) (interval * 1000);
        print = printInterval != 0;
    }

    /**
     * Function for thread-safe manipulation of the main follow-up Pixel object.
     * This function represents a critical section for all the threads, and the static data is shared within this critical section.
     * The function provides the next available pixel number each time it is called.
     *
     * @return true if the next pixel is allocated, false if there are no more pixels
     */
    public boolean nextPixel() {
        synchronized (mutexNext) {
            if (cRow == maxRows)
                return false;
            ++cCol;
            if (cCol < maxCols) {
                row = cRow;
                col = cCol;
                return true;
            }
            cCol = 0;
            ++cRow;
            if (cRow < maxRows) {
                row = cRow;
                col = cCol;
                return true;
            }
            return false;
        }
    }

    /**
     * Marks the pixel as processed.
     */
    static void pixelDone() {
        synchronized (mutexPixels) {
            ++pixels;
        }
    }

    /**
     * Waits for all pixels to be processed and prints the progress percentage.
     * This method must be called from the main thread.
     */
    public static void waitToFinish() {
        if (print)
            System.out.printf(PRINT_FORMAT, 0d);

        while (last < totalPixels) {
            printPixel();
            try {
                Thread.sleep(printInterval);
            } catch (InterruptedException ignore) {
                if (print)
                    System.out.print("");
            }
        }
        if (print)
            System.out.println("100.0%");
    }

    /**
     * Prints the progress percentage of the pixels.
     */
    public static void printPixel() {
        long current = pixels;
        if (print && last != current) {
            int percentage = (int) (1000L * current / totalPixels);
            if (lastPrinted != percentage) {
                last = current;
                lastPrinted = percentage;
                System.out.printf(PRINT_FORMAT, percentage / 10d);
            }
        }
    }
}
