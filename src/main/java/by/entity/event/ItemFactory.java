package by.entity.event;

import by.dao.old.ChangeInstance;
import by.dao.old.SelectDao;

public class ItemFactory {

    public static Item createItem(int id) {
     /*   switch (ItemFactory.getItemTypeDao().readItemType(id)) {
            case "CLOTHES":
                return new Clothes();
            case "BICYCLE":
                return new Bicycle();
            default:*/
                return null;
      //  }
    }

    public static ChangeInstance getItemInstanceDao(int id) {
      /*  switch (ItemFactory.getItemTypeDao().readItemType(id)) {
            case "CLOTHES":
                return ClothesDao.getInstance();
            case "BICYCLE":
                return BicycleDao.getInstance();
            default:*/
                return null;
     //   }
    }

    /*public static SelectItemStatusDaoImpl getItemStatusDao() {
        return SelectItemStatusDaoImpl.getInstance();
    }*/

 /*   public static SelectItemTypeDao getItemTypeDao() {
        return SelectItemTypeDao.getInstance();
    }*/

/*    public static SelectDefaultItemDao getDefaultItemDao() {
        return selectDefaultItemDao;
    }*/

    public static SelectDao getSelectItemDao(int id) {
      /*  switch (ItemFactory.getItemTypeDao().readItemType(id)) {
            case "CLOTHES":
                return SelectClothesDao.getInstance();
            case "BICYCLE":
                return SelectBicycleDao.getInstance();
            default:*/
                return null;
        //}
    }
}