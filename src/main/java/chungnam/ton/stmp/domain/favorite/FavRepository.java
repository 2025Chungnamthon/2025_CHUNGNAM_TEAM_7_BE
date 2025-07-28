package chungnam.ton.stmp.domain.favorite;

import chungnam.ton.stmp.domain.market.domain.Market;
import chungnam.ton.stmp.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavRepository extends JpaRepository<Fav, Long> {

    Optional<Fav> findByUserAndMarket(User user, Market market);

    List<Fav> findAllByUser(User user);

    void deleteByUserAndMarket(User user, Market market);
}
