package org.example.lab4.repository;

import org.example.lab4.model.entity.Notification;
import org.example.lab4.model.enums.NotificationChannel;
import org.example.lab4.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByStatus(NotificationStatus status);
    List<Notification> findByChannel(NotificationChannel channel);
    List<Notification> findByRecipientId(Long recipientId);

    List<Notification> findByStatusAndChannel(NotificationStatus status,
                                              NotificationChannel channel);

    List<Notification> findByStatusOrderByCreatedAtAsc(NotificationStatus status);

    @Query("""
        select n
        from Notification n
        where n.recipient.id = :recipientId
          and n.status = :status
        """)
    List<Notification> findByRecipientIdAndStatus(
            @Param("recipientId") Long recipientId,
            @Param("status") NotificationStatus status
    );

    List<Notification> findAllByOrderByCreatedAtDesc();
}