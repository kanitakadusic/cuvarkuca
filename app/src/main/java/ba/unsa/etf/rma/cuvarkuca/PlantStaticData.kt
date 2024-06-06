package ba.unsa.etf.rma.cuvarkuca

import ba.unsa.etf.rma.cuvarkuca.models.Biljka
import ba.unsa.etf.rma.cuvarkuca.models.KlimatskiTip
import ba.unsa.etf.rma.cuvarkuca.models.MedicinskaKorist
import ba.unsa.etf.rma.cuvarkuca.models.ProfilOkusaBiljke
import ba.unsa.etf.rma.cuvarkuca.models.Zemljiste

val biljke = listOf(
    Biljka(
        naziv = "Bosiljak (Ocimum basilicum)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
        jela = listOf("Salata od paradajza", "Punjene tikvice"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Nana (Mentha spicata)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        profilOkusa = ProfilOkusaBiljke.MENTA,
        jela = listOf("Jogurt sa voćem", "Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Kamilica (Matricaria chamomilla)",
        porodica = "Asteraceae (glavočike)",
        medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od kamilice"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ružmarin (Rosmarinus officinalis)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Pečeno pile", "Grah","Gulaš"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Lavanda (Lavandula angustifolia)",
        porodica = "Lamiaceae (metvice)",
        medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Jogurt sa voćem"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        naziv = "Ruža (Rosa)",
        porodica = "Rosaceae (ruže)",
        medicinskoUpozorenje = "Trudnicama i dojiljama se savjetuje oprez pri konzumaciji čaja od ruže. Tonik od ruže može biti rizičan za osobe koje su pretrpjele teške srčane poremećaje.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.SLATKI,
        jela = listOf("Sok od ruže", "Čaj od ruže", "Džem od ruže"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Čuvarkuća (Sempervivum tectorum)",
        porodica = "Crassulaceae (tustikovke)",
        medicinskoUpozorenje = "Konzumacija u većim količinama može izazvati probavne smetnje. Sok čuvarkuće može uzrokovati iritaciju kod osoba s osjetljivom kožom.",
        medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.GORKO,
        jela = listOf("Ljekoviti sok", "Umirujući čaj"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA, KlimatskiTip.PLANINSKA),
        zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.SLJUNKOVITO)
    ),
    Biljka(
        naziv = "Žalfija (Salvia officinalis)",
        porodica = "Lamiaceae (usnate)",
        medicinskoUpozorenje = "Može izazvati neurološke probleme i negativno utjecati na zdravlje jetre. U većim količinama može biti toksična.",
        medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTIVBOLOVA, MedicinskaKorist.REGULACIJAPROBAVE),
        profilOkusa = ProfilOkusaBiljke.AROMATICNO,
        jela = listOf("Čaj od žalfije"),
        klimatskiTipovi = listOf(KlimatskiTip.PLANINSKA, KlimatskiTip.TROPSKA),
        zemljisniTipovi = listOf(Zemljiste.CRNICA)
    ),
    Biljka(
        naziv = "Šipurak (Rosa canina)",
        porodica = "Rosaceae (ruže)",
        medicinskoUpozorenje = "Oprez je potreban kod osoba koje su sklone alergijama na šipurak ili ružu.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPRITISKA),
        profilOkusa = ProfilOkusaBiljke.SLATKI,
        jela = listOf("Pekmez od šipurka", "Slatko od šipurka", "Sok od šipurka"),
        klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.PLANINSKA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA)
    ),
    Biljka(
        naziv = "Limun (Citrus limon)",
        porodica = "Rutaceae (rutovke)",
        medicinskoUpozorenje = "Konzumacija u velikim količinama može oštetiti zubnu caklinu i uzrokovati karijes. Kod nekih osoba i refluks kiseline.",
        medicinskeKoristi = listOf(MedicinskaKorist.REGULACIJAPRITISKA, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        profilOkusa = ProfilOkusaBiljke.CITRUSNI,
        jela = listOf("Limunada", "Preljev za salate"),
        klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA),
        zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.PJESKOVITO)
    )
)