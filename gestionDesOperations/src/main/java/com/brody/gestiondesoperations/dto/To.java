package com.brody.gestiondesoperations.dto;

public class To {
	
	private String quotecurrency;
    private double mid;
    
	public To(String quotecurrency, double mid) {
		
		this.quotecurrency = quotecurrency;
		this.mid = mid;
	}

	public To() {
		super();
	}

	public String getQuotecurrency() {
		return quotecurrency;
	}

	public void setQuotecurrency(String quotecurrency) {
		this.quotecurrency = quotecurrency;
	}

	public double getMid() {
		return mid;
	}

	public void setMid(double mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "To [quotecurrency=" + quotecurrency + ", mid=" + mid + "]";
	}
}
