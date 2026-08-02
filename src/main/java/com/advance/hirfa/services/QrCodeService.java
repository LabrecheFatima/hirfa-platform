package com.advance.hirfa.services;

import com.advance.hirfa.domaine.entities.QrCode;
import com.advance.hirfa.domaine.entities.Ticket;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
}
