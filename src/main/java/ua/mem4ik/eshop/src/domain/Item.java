package ua.mem4ik.eshop.src.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title can't be empty")
    private String title = "No Title";
    @NotBlank(message = "Enter description or we will set a default one")
    private String description = "No Description";
    @NotBlank(message = "Add 1 image as well")
    private String image = "No Image";

    private Double price;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author = null;

    private Category category = Category.DIFFERENT;
}
