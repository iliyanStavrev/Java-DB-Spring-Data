package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("SELECT o FROM offers o " +
            "WHERE o.apartment.apartmentType = 'three_rooms' " +
            "ORDER BY o.apartment.area DESC, " +
            "o.price")
    List<Offer> findBestOffers();
}
