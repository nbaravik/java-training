package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @MapsId
    @OneToOne
    private int orderId;
    @MapsId
    @OneToOne
    private int productId;
    private int quantity;
}
