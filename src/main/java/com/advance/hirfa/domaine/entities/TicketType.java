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
@Table(name= "ticket_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketType {
    @Id
    @Column(name= "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name= "name", nullable = false)
    private String name;

    @Column(name= "price", nullable = false)
    private double price;

    @Column(name= "description", nullable = false)
    private String description;

    @Column(name= "total_available")
    private Integer total_available;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "ticketType", cascade = CascadeType.ALL)
    private List<Ticket> tickets =  new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @LastModifiedBy
    @Column(name = "updated_at", updatable = false)
    private LocalDate updateAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TicketType that = (TicketType) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(total_available, that.total_available) && Objects.equals(createAt, that.createAt) && Objects.equals(updateAt, that.updateAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, total_available, createAt, updateAt);
    }
}
