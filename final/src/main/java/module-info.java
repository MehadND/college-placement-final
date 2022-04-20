module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires org.apache.pdfbox;
	requires org.apache.poi.poi;
	requires org.apache.poi.ooxml;
	requires java.mail;
	requires org.jsoup;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}
