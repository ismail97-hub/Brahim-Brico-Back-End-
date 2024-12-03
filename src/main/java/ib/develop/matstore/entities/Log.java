package ib.develop.matstore.entities;

import ib.develop.matstore.common.enums.LogType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.TimeZoneColumn;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private long time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogType type;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhone;

    @ManyToOne
    private Order order;

    @Column(nullable = false)
    private double amount;

    public LocalTime getTime() {
        return new Timestamp(time).toLocalDateTime().toLocalTime();
    }
}
