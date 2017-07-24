package com.wissam.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Filters {
	private Date createdFrom;
	private Date createdTo;
	private Date lastModifiedFrom;
	private Date lastModifiedTo;
	private ArrayList<String> keywords;
	private boolean closed;
	private boolean inprogress;
	private boolean fixed;
	private boolean approved;
	private boolean reviewed;
	private boolean verified;

	private Filters(FiltersBuilder builder) {
		this.createdFrom = builder.createdFrom;
		this.createdTo = builder.createdTo;
		this.lastModifiedFrom = builder.lastModifiedFrom;
		this.lastModifiedTo = builder.lastModifiedTo;
		this.keywords = builder.keywords;
		this.closed = builder.closed;
		this.inprogress = builder.inprogress;
		this.fixed = builder.fixed;
		this.approved = builder.approved;
		this.reviewed = builder.reviewed;
		this.verified = builder.verified;
	}

	public Date getCreatedFrom() {
		return createdFrom;
	}

	public Date getCreatedTo() {
		return createdTo;
	}

	public Date getLastModifiedFrom() {
		return lastModifiedFrom;
	}

	public Date getLastModifiedTo() {
		return lastModifiedTo;
	}

	public ArrayList<String> getKeywords() {
		return keywords;
	}

	public boolean isClosed() {
		return closed;
	}

	public boolean isInprogress() {
		return inprogress;
	}

	public boolean isFixed() {
		return fixed;
	}

	public boolean isApproved() {
		return approved;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public boolean isVerified() {
		return verified;
	}

	public static class FiltersBuilder {
		private Date createdFrom;
		private Date createdTo;
		private Date lastModifiedFrom;
		private Date lastModifiedTo;
		private ArrayList<String> keywords;
		private boolean closed;
		private boolean inprogress;
		private boolean fixed;
		private boolean approved;
		private boolean reviewed;
		private boolean verified;

		public FiltersBuilder() {
			this.keywords = new ArrayList<>();
		}

		public FiltersBuilder createdFrom(Date createdFrom) {
			this.createdFrom = createdFrom;
			return this;
		}

		public FiltersBuilder createdTo(Date createdTo) {
			this.createdTo = createdTo;
			return this;
		}

		public FiltersBuilder lastModifiedFrom(Date lastModifiedFrom) {
			this.lastModifiedFrom = lastModifiedFrom;
			return this;
		}

		public FiltersBuilder lastModifiedTo(Date lastModifiedTo) {
			this.lastModifiedTo = lastModifiedTo;
			return this;
		}

		public FiltersBuilder keywords(List<String> keywords) {
			if (keywords == null)
				return this;
			this.keywords.addAll(keywords);

			return this;
		}

		public FiltersBuilder crClosed(boolean closed) {
			this.closed = closed;
			return this;
		}

		public FiltersBuilder crInprogress(boolean inprogress) {
			this.inprogress = inprogress;
			return this;
		}

		public FiltersBuilder crFixed(boolean fixed) {
			this.fixed = fixed;
			return this;
		}

		public FiltersBuilder crApproved(boolean approved) {
			this.approved = approved;
			return this;
		}

		public FiltersBuilder crReviewed(boolean reviewed) {
			this.reviewed = reviewed;
			return this;
		}

		public FiltersBuilder crVerified(boolean verified) {
			this.verified = verified;
			return this;
		}

		public Filters build() {
			return new Filters(this);
		}
	}
}
