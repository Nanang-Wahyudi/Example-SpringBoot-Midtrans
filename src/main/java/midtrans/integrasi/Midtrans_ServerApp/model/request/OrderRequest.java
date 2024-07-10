package midtrans.integrasi.Midtrans_ServerApp.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequest {

    private Long productId;

    private String customerEmail;

    private String customerName;

    private int quantity;

}
