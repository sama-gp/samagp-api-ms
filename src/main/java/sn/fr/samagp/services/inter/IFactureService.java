package sn.fr.samagp.services.inter;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public interface IFactureService {
    ByteArrayInputStream genererFacturePdf(UUID paiementId);
}