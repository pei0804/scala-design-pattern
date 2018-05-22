package Iterator

object Main {
  def main(args: Array[String]) {
    val bookShelf = new BookShelf(maxsize = 4)

    bookShelf.appendBook(new Book("良い本"))
    bookShelf.appendBook(new Book("だめ本"))
    bookShelf.appendBook(new Book("そこそこ本"))
    bookShelf.appendBook(new Book("まあまあ本"))

    val it = bookShelf.iterator()
    while (it.hasNext()) {
      val book = it.next()
      println(book.getName())
    }
  }
}
