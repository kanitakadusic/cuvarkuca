package ba.unsa.etf.rma.cuvarkuca

val biljke = listOf(
    Biljka(
        "Bosiljak (Ocimum basilicum)",
        "Lamiaceae (usnate)",
        "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
        listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
        ProfilOkusaBiljke.BEZUKUSNO,
        listOf("Salata od paradajza", "Punjene tikvice"),
        listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
        listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA)
    ),
    Biljka(
        "Nana (Mentha spicata)",
        "Lamiaceae (metvice)",
        "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
        listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
        ProfilOkusaBiljke.MENTA,
        listOf("Jogurt sa voćem", "Gulaš"),
        listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        listOf(Zemljiste.GLINENO, Zemljiste.CRNICA)
    ),
    Biljka(
        "Kamilica (Matricaria chamomilla)",
        "Asteraceae (glavočike)",
        "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
        listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
        ProfilOkusaBiljke.AROMATICNO,
        listOf("Čaj od kamilice"),
        listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
        listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        "Ružmarin (Rosmarinus officinalis)",
        "Lamiaceae (metvice)",
        "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
        listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
        ProfilOkusaBiljke.AROMATICNO,
        listOf("Pečeno pile", "Grah","Gulaš"),
        listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        listOf(Zemljiste.SLJUNKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        "Lavanda (Lavandula angustifolia)",
        "Lamiaceae (metvice)",
        "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
        listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
        ProfilOkusaBiljke.AROMATICNO,
        listOf("Jogurt sa voćem"),
        listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
        listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO)
    ),
    Biljka(
        "Ruža (Rosa)",
        "Rosaceae (ruže)",
        "Trudnicama i dojiljama se savjetuje oprez pri konzumaciji čaja od ruže. Tonik od ruže može biti rizičan za osobe koje su pretrpjele teške srčane poremećaje.",
        listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        ProfilOkusaBiljke.SLATKI,
        listOf("Sok", "Čaj", "Džem"),
        listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
        listOf(Zemljiste.ILOVACA)
    ),
    Biljka(
        "Čuvarkuća (Sempervivum tectorum)",
        "Crassulaceae (tustikovke)",
        "Konzumacija u većim količinama može izazvati probavne smetnje. Sok čuvarkuće može uzrokovati iritaciju kod osoba s osjetljivom kožom.",
        listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
        ProfilOkusaBiljke.GORKO,
        listOf("Ljekoviti sok", "Umirujući čaj"),
        listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA, KlimatskiTip.PLANINSKA),
        listOf(Zemljiste.PJESKOVITO, Zemljiste.SLJUNKOVITO)
    ),
    Biljka(
        "Žalfija (Salvia officinalis)",
        "Lamiaceae (usnate)",
        "Može izazvati neurološke probleme i negativno utjecati na zdravlje jetre. U većim količinama može biti toksična.",
        listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTIVBOLOVA, MedicinskaKorist.REGULACIJAPROBAVE),
        ProfilOkusaBiljke.AROMATICNO,
        listOf("Čaj"),
        listOf(KlimatskiTip.PLANINSKA, KlimatskiTip.TROPSKA),
        listOf(Zemljiste.CRNICA)
    ),
    Biljka(
        "Šipurak (Rosa canina)",
        "Rosaceae (ruže)",
        "Oprez je potreban kod osoba koje su sklone alergijama na šipurak ili ružu.",
        listOf(MedicinskaKorist.REGULACIJAPROBAVE, MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPRITISKA),
        ProfilOkusaBiljke.SLATKI,
        listOf("Pekmez", "Slatko", "Sok"),
        listOf(KlimatskiTip.UMJERENA, KlimatskiTip.PLANINSKA),
        listOf(Zemljiste.ILOVACA)
    ),
    Biljka(
        "Limun (Citrus limon)",
        "Rutaceae (rutovke)",
        "Konzumacija u velikim količinama može oštetiti zubnu caklinu i uzrokovati karijes. Kod nekih osoba i refluks kiseline.",
        listOf(MedicinskaKorist.REGULACIJAPRITISKA, MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PODRSKAIMUNITETU),
        ProfilOkusaBiljke.CITRUSNI,
        listOf("Limunada", "Preljev za salate"),
        listOf(KlimatskiTip.SREDOZEMNA),
        listOf(Zemljiste.ILOVACA, Zemljiste.PJESKOVITO)
    )
)