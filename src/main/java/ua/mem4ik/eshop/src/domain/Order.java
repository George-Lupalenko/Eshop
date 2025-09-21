package ua.mem4ik.eshop.src.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "items_id")
    private Item items;

    @OneToOne
    private User customer;

    private String status;
    private String comment;
    private Date date;
    private String address;
}
