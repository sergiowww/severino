package br.mp.mpt.prt8.severino.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.parameter.ColumnParameter;
import org.springframework.data.jpa.datatables.parameter.OrderParameter;

/**
 * Utilitários para o data table.
 * 
 * @author sergio.eoliveira
 *
 */
public class DataTableUtils {
	/**
	 * Creates a 'LIMIT .. OFFSET .. ORDER BY ..' clause for the given
	 * {@link DataTablesInput}.
	 * 
	 * @param input
	 *            the {@link DataTablesInput} mapped from the Ajax request
	 * @return a {@link Pageable}, must not be {@literal null}.
	 */
	public static Pageable getPageable(DataTablesInput input) {
		List<Order> orders = new ArrayList<Order>();
		for (OrderParameter order : input.getOrder()) {
			ColumnParameter column = input.getColumns().get(order.getColumn());
			if (column.getOrderable()) {
				String sortColumn = column.getData();
				Direction sortDirection = Direction.fromString(order.getDir());
				orders.add(new Order(sortDirection, sortColumn));
			}
		}
		Sort sort = orders.isEmpty() ? null : new Sort(orders);

		if (input.getLength() == -1) {
			input.setStart(0);
			input.setLength(Integer.MAX_VALUE);
		}
		return new PageRequest(input.getStart() / input.getLength(), input.getLength(), sort);
	}

	/**
	 * Converter a pagina para o data tables output.
	 * 
	 * @param page
	 * @param input
	 * @return
	 */
	public static <T> DataTablesOutput<T> toOutput(Page<T> page, DataTablesInput input) {
		DataTablesOutput<T> output = new DataTablesOutput<T>();
		output.setDraw(input.getDraw());

		try {
			long recordsTotal = page.getTotalElements();
			if (recordsTotal == 0) {
				return output;
			}
			output.setRecordsTotal(recordsTotal);

			output.setData(page.getContent());
			output.setRecordsFiltered(page.getTotalElements());

		} catch (Exception e) {
			output.setError(e.toString());
			output.setRecordsFiltered(0L);
		}

		return output;
	}
}
