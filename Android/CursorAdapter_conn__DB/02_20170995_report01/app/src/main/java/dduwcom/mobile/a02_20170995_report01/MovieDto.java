package dduwcom.mobile.a02_20170995_report01;

import java.io.Serializable;

/*
    하나의 주소 정보를 저장하기 위한 DTO
    Intent 에 저장 가능하게 하기 위하여
    Serializable 인터페이스를 구현함
*/

public class MovieDto implements Serializable {
    long _id;
    String title;
    String genre;
    String hero;
    String director;
    String rating;
    String premiere;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_id);
        sb.append(".\t\t");
        sb.append(title);
        sb.append("\t\t");
        sb.append(genre);
        sb.append("\t\t");
        sb.append(hero);
        sb.append("\t\t");
        sb.append(director);
        sb.append("\t\t");
        sb.append(rating);
        sb.append("\t\t");
        sb.append(premiere);
        return sb.toString();
    }
}
