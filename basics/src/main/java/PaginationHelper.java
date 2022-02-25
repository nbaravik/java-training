import java.util.List;

public class PaginationHelper {

    private List<String> collection;
    private int itemsPerPage;

    /**
     * The constructor takes in an array of items and a integer indicating how many
     * items fit within a single page
     */
    public PaginationHelper(List<String> collection, int itemsPerPage) {
        if (itemsPerPage <= 0) {
            throw new IllegalArgumentException("Pagination doesn't work with items per page <= 0.");
        }
        if (collection == null) {
            throw new NullPointerException("Collection must be provided.");
        }
        this.collection = collection;
        this.itemsPerPage = itemsPerPage;
    }

    /**
     * returns the number of items within the entire collection
     */
    public int itemCount() {

        int size = this.collection.size();
        return size;
    }

    /**
     * returns the number of pages
     */
    public int pageCount() {
        int remainder = itemCount() % this.itemsPerPage;
        // return itemCount() / this.itemsPerPage + remainder == 0 ? 0 : 1;
        if (remainder == 0) {
            return itemCount() / this.itemsPerPage;
        } else {
            return itemCount() / this.itemsPerPage + 1;
        }
    }

    /**
     * returns the number of items on the current page. page_index is zero based.
     * this method should return -1 for pageIndex values that are out of range
     */
    public int pageItemCount(int pageIndex) {
        int lastPageIndex = this.pageCount() - 1;
        int remainder = itemCount() % this.itemsPerPage;
        if (lastPageIndex < pageIndex || pageIndex < 0) {
            return -1;
        }
        if (pageIndex == lastPageIndex && remainder > 0) {
                return remainder;
        }
        return this.itemsPerPage;
    }

    /**
     * determines what page an item is on. Zero based indexes
     * this method should return -1 for itemIndex values that are out of range
     */
    public int pageIndex(int itemIndex) {
        if (this.itemCount() <= itemIndex || itemIndex < 0) {
            return -1;
        }
        return itemIndex / this.itemsPerPage;
    }
}
