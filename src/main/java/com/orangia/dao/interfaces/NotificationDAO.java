package com.orangia.dao.interfaces;

import com.orangia.model.Notification;
import java.util.List;
import java.util.Optional;

public interface NotificationDAO {

    Optional<Notification> findById(Long idNotification);

    List<Notification> findAll();

    List<Notification> findByDestinataireId(Long idUtilisateur);

    List<Notification> findUnreadByDestinataireId(Long idUtilisateur);

    Notification save(Notification notification);

    void update(Notification notification);

    void markAsRead(Long idNotification);
}
