package com.advance.hirfa.services.impl;

import com.advance.hirfa.domaine.entities.Ticket;
import com.advance.hirfa.domaine.entities.TicketStatusEnum;
import com.advance.hirfa.domaine.entities.TicketType;
import com.advance.hirfa.domaine.entities.User;
import com.advance.hirfa.exceptions.TicketSoldOutExceptions;
import com.advance.hirfa.exceptions.TicketTypeNotFoundExceptions;
import com.advance.hirfa.exceptions.UserNotFoundExceptions;
import com.advance.hirfa.repository.TicketRepository;
import com.advance.hirfa.repository.TicketTypeRepository;
import com.advance.hirfa.repository.UserRepository;
import com.advance.hirfa.services.QrCodeService;
import com.advance.hirfa.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundExceptions(
                        String.format("User with ID %s was not found", userId)
                ));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundExceptions(
                        String.format("Ticket type with ID %s was not found", ticketTypeId)
                ));

        int purchasedTickets = ticketRepository.countByTicketType(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if (purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutExceptions("No more tickets available for this tier!");
        }

        Ticket ticket = Ticket.builder()
                .purchase(user)
                .ticketType(ticketType)
                .status(TicketStatusEnum.PURCHASED)
                .createAt(LocalDateTime.now())
                .build();

        Ticket savedTicket= ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }

}
