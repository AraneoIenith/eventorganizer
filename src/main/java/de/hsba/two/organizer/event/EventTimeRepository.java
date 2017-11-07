package de.hsba.two.organizer.event;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventTimeRepository extends JpaRepository<EventTime, Long> {
}
