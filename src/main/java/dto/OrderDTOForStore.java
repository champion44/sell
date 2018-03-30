package dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTOForStore {
		
	private String time;
	
	private Double total;
	
	private Double refund;
	
	private List<OrderDTO> OrderDTOForStoreList;
}
