import java.util.ArrayList;
import java.util.List;

public class LottoRepository {

    // field
    // lotto list
    List<Lotto> lottoList;

    public LottoRepository(){
        lottoList = new ArrayList<>();
    }

    // method
    // insertLotto
    public void insertLotto(Lotto newLotto){
        lottoList.add(newLotto);
    }

    // getLottoList
    public List<Lotto> getLottoList() {
        return lottoList;
    }

    public void clearLotto() {
        lottoList.clear();
    }
}
