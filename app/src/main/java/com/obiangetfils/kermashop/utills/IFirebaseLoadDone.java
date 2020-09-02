package com.obiangetfils.kermashop.utills;

import java.util.List;

public interface IFirebaseLoadDone {

    void onFirebaseLoadSuccess(List<ImagesStories> imagesStoriesList);
    void onFirebaseLoadFailed(String message);

}
