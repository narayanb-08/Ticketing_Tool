package com.sumpositive.ticketing.ticket.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.sumpositive.ticketing.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 150)
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private TicketStatus status = TicketStatus.TO_DO; 
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TicketPriority priority = TicketPriority.MEDIUM;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id")
	private User assignee;
	
	@Column(name = "due_date")
	private LocalDate dueDate;
	
	@Column(nullable = false, updatable = false)
	private OffsetDateTime createdAt;
	
	@Column(nullable = false, updatable = false)
	private OffsetDateTime updatedAt;
	
	public Ticket() {
		
	}
	
	@PrePersist
	protected void onCreate() {
		OffsetDateTime now  =OffsetDateTime.now(ZoneOffset.UTC);
		createdAt = now;
		updatedAt = now;
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
	}
}