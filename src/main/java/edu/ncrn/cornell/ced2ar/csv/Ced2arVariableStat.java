package edu.ncrn.cornell.ced2ar.csv;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.util.StringUtils;

public class Ced2arVariableStat implements Serializable {
	private static final long serialVersionUID = 128949712050474331L;

	private String name;
	private String label;
	private Long validCount = 0L;
	private Long invalidCount = 0L;
	private Long possibleErrorValueCount = 0L;
	private Double minValue;
	private Double maxValue;
	private Double stdDeviation = null;
	private HashMap<String, String> missingValues = new HashMap<String, String>();
	private HashMap<String, String> validValues = new HashMap<String, String>(); // Value

	private boolean isNumeric;
	private boolean isDate;
	private int startPosition;
	private int endPosition;
	private int variableNumber;
	//private DescriptiveStatistics stats = new DescriptiveStatistics();
	private SummaryStatistics stats = new SummaryStatistics();

	public Long getPossibleErrorValueCount() {
		return possibleErrorValueCount;
	}

	public void setPossibleErrorValueCount(Long possibleErrorValueCount) {
		this.possibleErrorValueCount = possibleErrorValueCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return decodeComma(label);
	}

	public void setLabel(String label) {
		this.label = encodeComma(label);
	}

	public Long getValidCount() {
		return validCount;
	}

	public void setValidCount(Long validCount) {
		this.validCount = validCount;
	}

	public Long getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(Long invalidCount) {
		this.invalidCount = invalidCount;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public HashMap<String, String> getMissingValues() {
		return missingValues;
	}

	public void setStdDeviation(double stdDeviation) {
		this.stdDeviation = stdDeviation;
	}

	public void setMissingValues(HashMap<String, String> missingValues) {
		this.missingValues = missingValues;
	}

	public HashMap<String, String> getValidValues() {
		return validValues;
	}

	public void setValidValues(HashMap<String, String> validValues) {
		this.validValues = validValues;
	}

	public boolean isNumeric() {
		return isNumeric;
	}

	public void setNumeric(boolean isNumeric) {
		this.isNumeric = isNumeric;
	}

	public boolean isDate() {
		return isDate;
	}

	public void setDate(boolean isDate) {
		this.isDate = isDate;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	public int getVariableNumber() {
		return variableNumber;
	}

	public void setVariableNumber(int variableNumber) {
		this.variableNumber = variableNumber;
	}

	public SummaryStatistics getStats() {
		return stats;
	}

	public void setStats(SummaryStatistics stats) {
		this.stats = stats;
	}

	public String getCSVValue(boolean summaryStats) {
		String csvString = "";
		String emptyString = "";
		String min = "";
		String max = "";
		String stdDev = "";
		String mean = "";
		if (isNumeric && summaryStats) {
			min = ((Double) stats.getMin()).equals(Double.NaN) ? emptyString
					: "" + stats.getMin();
			max = ((Double) stats.getMax()).equals(Double.NaN) ? emptyString
					: "" + stats.getMax();
			stdDev = ((Double) stats.getStandardDeviation()).equals(Double.NaN) ? emptyString
					: "" + stats.getStandardDeviation();
			mean = ((Double) stats.getMean()).equals(Double.NaN) ? emptyString
					: "" + stats.getMean();
			csvString = name + "," + getLabel() + "," + validCount + ","
					+ invalidCount + "," + min + "," + max + "," + mean + ","
					+ stdDev;
		} else {
			csvString = name + "," + getLabel() + "," + validCount + ","
					+ invalidCount + "," + min + "," + max + "," + mean + ","
					+ stdDev;
		}
		return csvString;
	}

	@Override
	public String toString() {
		return getCSVValue(true);
	}

	private String encodeComma(String valueString) {
		if (StringUtils.isEmpty(valueString))
			return "";
		return valueString.replace(",", "%2C");
	}

	private String decodeComma(String valueString) {
		if (StringUtils.isEmpty(valueString))
			return "";
		return valueString.replace("%2C", ",");
	}
}
