package ua.mem4ik.eshop.src.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotBlank(message = "Enter description or we will set a default one")
    private String description;
    @NotBlank(message = "Add 1 image as well")
    private String image;

}
