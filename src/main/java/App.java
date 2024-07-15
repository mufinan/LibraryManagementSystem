import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("library");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        //Author Ekleme
        Author author = new Author();
        author.setName("Mustafa Furkan İnan");
        author.setCountry("Türkiye");
        author.setBirthDate(LocalDate.now());

        //Publisher Ekleme
        Publisher publisher = new Publisher();
        publisher.setName("İnan Yayıncılık");
        publisher.setEstablishmentYear(LocalDate.now());
        publisher.setAddress("Bursa, Görükle");

        //Category Oluşturma
        Category aksiyon = new Category("Aksiyon","Aksiyon Kitapları");
        Category tarih = new Category("Tarih","Tarih Kitapları");

        //Book Ekleme
        Book book = new Book();
        book.setName("Para Davası");
        book.setPublicationYear(LocalDate.now());
        book.setStock(20);
        book.setAuthor(author);
        book.setPublisher(publisher);

        //Category Ekleme
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(aksiyon);
        categoryList.add(tarih);
        book.setCategoryList(categoryList);

        //Borrowing Ekleme
        BookBorrowing borrowing = new BookBorrowing();
        borrowing.setName("İlknur İnan");
        borrowing.setBorrowingDate(LocalDate.now());
        borrowing.setBook(book);

        transaction.begin();
        //Borrowing ile birlikte tüm entityleri db ye yazar.(cascade)
        entityManager.persist(borrowing);

        BookBorrowing getBorrowing = entityManager.find(BookBorrowing.class,1);
        System.out.println(getBorrowing.toString()); //Borrowing Görüntüleme

        getBorrowing.setReturnDate(LocalDate.now());
        entityManager.merge(getBorrowing); //İade sonrası book_borrowings tablosunu günceller.
        transaction.commit();
    }
}
