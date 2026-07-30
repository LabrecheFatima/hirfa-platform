package com.advance.hirfa.domaine;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name= "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="start")
    private LocalDateTime start;

    @Column(name="end")
    private LocalDateTime end;

    @Column(name="venue", nullable = false)
    private String venue;

    @Column(name="sales_start")
    private LocalDateTime SalesStart;

    @Column(name="sales_end")
    private LocalDateTime SalesEnd;

    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToMany(mappedBy= "attendingEvents")
    private List<User> attendees= new ArrayList<>();

    @ManyToMany(mappedBy= "staffingEvents")
    private List<User> staff= new ArrayList<>();

    @OneToMany(mappedBy= "event", cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @LastModifiedBy
    @Column(name = "updated_at", updatable = false)
    private LocalDate updateAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) && Objects.equals(name, event.name) && Objects.equals(start, event.start) && Objects.equals(end, event.end) && Objects.equals(venue, event.venue) && Objects.equals(SalesStart, event.SalesStart) && Objects.equals(SalesEnd, event.SalesEnd) && Objects.equals(status, event.status) && Objects.equals(createAt, event.createAt) && Objects.equals(updateAt, event.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, start, end, venue, SalesStart, SalesEnd, status, createAt, updateAt);
    }
}
