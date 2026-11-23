
package com.orangia.dao.interfaces;

import com.orangia.model.Notification;
import java.util.List;

public interface NotificationDAO {

    Notification findById(int idNotification);

    List<Notification> findAll();

    List<Notification> findByDestinataireId(int idDestinataire);

    List<Notification> findUnreadByDestinataireId(int idDestinataire);

    void save(Notification notification);

    void update(Notification notification);

    void markAsRead(int idNotification);
}
