package br.mp.mpt.prt8.severino.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

/**
 * Utilitários para o data table.
 * 
 * @author sergio.eoliveira
 *
 */
public class DataTableUtils {

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
