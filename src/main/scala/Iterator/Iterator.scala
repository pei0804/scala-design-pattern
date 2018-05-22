package Iterator

trait Aggregate {
  def iterator(): Iterator
}

trait Iterator {
  def hasNext(): Boolean

  def next(): Object
}

class Book(n: String) {
  val name: String = n

  def getName(): String =
    this.name
}


class BookShelf(maxsize: Int) extends Aggregate {
  private val books = new Array[Book](maxsize)
  private var last = 0

  def getBookAt(index: Int): Book =
    books(index)

  def appendBook(book: Book) = {
    this.books(last) = book
    last += 1
  }

  def getLength(): Int =
    last

  def iterator(): BookShelfIterator =
    new BookShelfIterator(bookShelf = this)
}


class BookShelfIterator(bookShelf: BookShelf) extends Iterator {
  private var index = 0

  def hasNext(): Boolean =
    index < bookShelf.getLength()

  def next(): Book = {
    val book = bookShelf.getBookAt(index)
    index += 1
    book
  }
}
