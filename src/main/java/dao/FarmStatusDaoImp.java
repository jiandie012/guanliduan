
package dao;
import tapDame.pojo.FarmStatus;
public interface FarmStatusDao {
    FarmStatus findByFId(String id);
    void addFarmStatus(FarmStatus farmStatus);
    void updateFarmStatus(FarmStatus farmStatus);
    void delFarmStatus(String id);
}
