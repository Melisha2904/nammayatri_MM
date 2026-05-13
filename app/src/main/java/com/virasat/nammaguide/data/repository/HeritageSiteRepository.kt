package com.virasat.nammaguide.data.repository

import com.virasat.nammaguide.data.model.HeritageSite
import com.virasat.nammaguide.data.model.SiteCategory

object HeritageSiteRepository {

    val allSites = listOf(
        HeritageSite(
            id = "SITE_001",
            nameEn = "Hoysaleswara Temple",
            nameKn = "ಹೊಯ್ಸಳೇಶ್ವರ ದೇವಾಲಯ",
            location = "Halebidu, Hassan District",
            latitude = 13.2123, longitude = 75.9985,
            distanceKm = 3.2,
            era = "Hoysala – 12th Century CE",
            dynastyEn = "Hoysala Empire",
            dynastyKn = "ಹೊಯ್ಸಳ ಸಾಮ್ರಾಜ್ಯ",
            shortDescEn = "A masterpiece of Hoysala craftsmanship with over 20,000 intricately carved figures.",
            shortDescKn = "20,000 ಕ್ಕೂ ಹೆಚ್ಚು ಕೆತ್ತನೆಗಳನ್ನು ಹೊಂದಿರುವ ಹೊಯ್ಸಳ ಶಿಲ್ಪಕಲೆಯ ಮಾಸ್ಟರ್‌ಪೀಸ್.",
            fullDescEn = """Hoysaleswara Temple, located in the ancient town of Halebidu (formerly Dwarasamudra), stands as one of the most magnificent examples of Hoysala architecture in India. Built during the reign of King Vishnuvardhana in the 12th century, the temple was dedicated to Lord Shiva and named after Queen Shanthala's consort, King Hoysaleswara.

The temple complex consists of twin shrines — Hoysaleswara and Shanthaleswara — each with its own Nandi (sacred bull). The outer walls are covered with an extraordinary frieze of sculpted figures arranged in horizontal bands: elephants (symbolizing stability), lions (symbolizing courage), scrolling foliage, horses, mythological scenes, makaras (mythical sea creatures), swans, and deities.

What makes this temple unique is the chloritic schite (soapstone) used for construction — a stone soft enough to carve when quarried but hardens over time. Artisans exploited this property to create jewelry-like detail on a massive architectural canvas.

The temple was ransacked by Malik Kafur's forces in 1311 CE, accounting for its missing shikhara (tower). Despite this loss, what remains is considered a UNESCO World Heritage Site and draws thousands of art historians and pilgrims each year.""",
            fullDescKn = """ಹೊಯ್ಸಳೇಶ್ವರ ದೇವಾಲಯವು ಪ್ರಾಚೀನ ಪಟ್ಟಣ ಹಳೇಬೀಡಿನಲ್ಲಿ ನೆಲೆಸಿದ್ದು, ಭಾರತದ ಅತ್ಯಂತ ಸ್ಮಾರಕ ಹೊಯ್ಸಳ ವಾಸ್ತುಶಿಲ್ಪದ ಉದಾಹರಣೆಗಳಲ್ಲಿ ಒಂದಾಗಿ ಪ್ರಸಿದ್ಧವಾಗಿದೆ. 12ನೇ ಶತಮಾನದಲ್ಲಿ ರಾಜ ವಿಷ್ಣುವರ್ಧನನ ಆಳ್ವಿಕೆಯಲ್ಲಿ ನಿರ್ಮಿಸಲಾಯಿತು.

ದೇವಾಲಯದ ಸಂಕೀರ್ಣದಲ್ಲಿ ಹೊಯ್ಸಳೇಶ್ವರ ಮತ್ತು ಶಾಂತಳೇಶ್ವರ ಎಂಬ ಎರಡು ದೇಗುಲಗಳಿವೆ. ಹೊರಗೋಡೆಗಳಲ್ಲಿ ಆನೆ, ಸಿಂಹ, ದೇವತೆಗಳ ಸಾಲುಗಳ ಅದ್ಭುತ ಕೆತ್ತನೆ ಇದೆ.""",
            architecturalSignificance = "Stellate (star-shaped) platform plan, horizontal sculpture friezes, chloritic schist stone carving — considered the zenith of Hoysala art.",
            localLegend = "Legend says the temple took 103 years to build because artisans refused to rush, believing each sculpture was a prayer offered in stone to Lord Shiva.",
            hiddenFact = "🔍 Hidden Fact: The temple has no mortar — every stone is interlocked with iron dowels and gravity alone. The builders calculated that the weight of stone on stone would hold the structure for millennia!",
            tags = listOf("Hoysala", "UNESCO", "Shiva Temple", "Soapstone", "12th Century"),
            category = SiteCategory.TEMPLE
        ),
        HeritageSite(
            id = "SITE_002",
            nameEn = "Chennakeshava Temple",
            nameKn = "ಚೆನ್ನಕೇಶವ ದೇವಾಲಯ",
            location = "Belur, Hassan District",
            latitude = 13.1651, longitude = 75.8685,
            distanceKm = 6.7,
            era = "Hoysala – 1117 CE",
            dynastyEn = "Hoysala Empire",
            dynastyKn = "ಹೊಯ್ಸಳ ಸಾಮ್ರಾಜ್ಯ",
            shortDescEn = "Built to celebrate the Hoysala victory over the Cholas, this temple took 103 years to complete.",
            shortDescKn = "ಚೋಳರ ಮೇಲಿನ ಹೊಯ್ಸಳ ವಿಜಯವನ್ನು ಆಚರಿಸಲು ನಿರ್ಮಿಸಲಾದ ದೇವಾಲಯ.",
            fullDescEn = """The Chennakeshava Temple at Belur was commissioned by King Vishnuvardhana in 1117 CE to celebrate his victory over the Chola forces at the Battle of Talakad. The name "Chennakeshava" means "Handsome Vishnu" in Kannada, and the presiding deity is an exquisite form of Lord Vishnu.

Unlike Halebidu, this temple is still an active place of worship, with rituals performed daily. The tower (shikhara) is intact, making it unique among Hoysala temples. The bracket figures (Madanikas or Salabhanjikas) on the outer walls are considered the most beautiful sculptures in southern India — each represents a celestial beauty in a different pose.

The temple's inner sanctum glows with oil lamps, and the intricately carved door guardians (Dwarapalas) stand 3 meters tall. The ceiling panels inside are carved in mesmerizing concentric circles, each ring depicting a different mythological story.""",
            fullDescKn = """ಬೇಲೂರಿನ ಚೆನ್ನಕೇಶವ ದೇವಾಲಯವನ್ನು ರಾಜ ವಿಷ್ಣುವರ್ಧನನು 1117 ಸಿಇನಲ್ಲಿ ತಾಳಕಾಡು ಕದನದಲ್ಲಿ ಚೋಳರ ಮೇಲೆ ಸಾಧಿಸಿದ ವಿಜಯದ ಸ್ಮರಣೆಗಾಗಿ ನಿರ್ಮಿಸಿದ. "ಚೆನ್ನಕೇಶವ" ಎಂದರೆ ಕನ್ನಡದಲ್ಲಿ "ಸುಂದರ ವಿಷ್ಣು" ಎಂದರ್ಥ.""",
            architecturalSignificance = "Fully intact shikhara, active worship, Madanika bracket figures regarded as pinnacle of Hoysala sculpture.",
            localLegend = "The chief sculptor Jakanacharya was so gifted that a young boy — supposedly his illegitimate son — found a flaw in his carvings. The boy proved his skill and was taken on as an apprentice, continuing the temple's legacy.",
            hiddenFact = "🔍 Hidden Fact: There are 42 different Madanika (celestial dancer) sculptures, and no two are in the same pose. Each holds a different musical instrument — together they represent a complete classical concert frozen in stone!",
            tags = listOf("Hoysala", "Vishnu", "Active Temple", "UNESCO", "1117 CE"),
            category = SiteCategory.TEMPLE
        ),
        HeritageSite(
            id = "SITE_003",
            nameEn = "Hire Benakal Megalithic Site",
            nameKn = "ಹಿರೇ ಬೆಣಕಲ್ ಮಹಾಶಿಲಾ ತಾಣ",
            location = "Hire Benakal, Koppal District",
            latitude = 15.3421, longitude = 76.1876,
            distanceKm = 12.5,
            era = "Iron Age – 1000–300 BCE",
            dynastyEn = "Pre-historic Iron Age",
            dynastyKn = "ಪ್ರಾಗೈತಿಹಾಸಿಕ ಕಬ್ಬಿಣ ಯುಗ",
            shortDescEn = "Over 2,000 megalithic burial chambers spread across Deccan plateau — Karnataka's Stonehenge.",
            shortDescKn = "ಡೆಕ್ಕನ್ ಪ್ರಸ್ಥಭೂಮಿಯಲ್ಲಿ 2,000 ಮಹಾಶಿಲಾ ಸಮಾಧಿ ಕೊಠಡಿಗಳು — ಕರ್ನಾಟಕದ ಸ್ಟೋನ್‌ಹೆಂಜ್.",
            fullDescEn = """Hire Benakal is one of the largest megalithic burial sites in peninsular India, hidden away in the rocky Deccan landscape of Koppal district. Dating back to the Iron Age (approximately 1000–300 BCE), this site contains over 2,000 dolmens — stone burial chambers made of massive granite slabs.

The site stretches over 5 square kilometres and is believed to be a necropolis (city of the dead) for an ancient Iron Age community. Each dolmen typically consists of three upright stones topped by a capstone, forming a chamber where human remains and grave goods (pottery, iron implements, beads) were placed.

What is haunting and beautiful about this site is its silence. Surrounded by scrub forest and black granite outcrops, the dolmens seem to grow naturally from the earth. Archaeological excavations here have yielded iron swords, copper ornaments, and red and black ware pottery characteristic of the South Indian Iron Age.

Despite its archaeological significance, Hire Benakal appears on almost no tourist maps — making every visit feel like a private discovery.""",
            fullDescKn = """ಹಿರೇ ಬೆಣಕಲ್ ಪ್ರಾಯದ್ವೀಪ ಭಾರತದ ಅತ್ಯಂತ ದೊಡ್ಡ ಮಹಾಶಿಲಾ ಸಮಾಧಿ ತಾಣಗಳಲ್ಲಿ ಒಂದಾಗಿದ್ದು, ಕೊಪ್ಪಳ ಜಿಲ್ಲೆಯ ಬಂಡೆಮಯ ಡೆಕ್ಕನ್ ಭೂದೃಶ್ಯದಲ್ಲಿ ಅಡಗಿದೆ. ಕಬ್ಬಿಣ ಯುಗಕ್ಕೆ ಸೇರಿದ ಈ ತಾಣದಲ್ಲಿ 2,000 ಕ್ಕೂ ಹೆಚ್ಚು ಡಾಲ್ಮೆನ್‌ಗಳಿವೆ.""",
            architecturalSignificance = "Largest Iron Age megalithic necropolis in South India. Dolmens constructed without mortar using precise granite slab stacking.",
            localLegend = "Local shepherd communities call the site 'Pandavara Gudi' (Pandava Temples), believing the Pandavas built these stone chambers during their exile in the forests.",
            hiddenFact = "🔍 Hidden Fact: Archaeologists found that some dolmens have a small 'porthole' — a circular hole cut in the entrance slab. This was not for ventilation but to allow the spirit of the deceased to travel freely between worlds!",
            tags = listOf("Megalithic", "Iron Age", "Dolmen", "Prehistoric", "Koppal"),
            category = SiteCategory.MONUMENT
        ),
        HeritageSite(
            id = "SITE_004",
            nameEn = "Aihole Rock Inscriptions",
            nameKn = "ಐಹೊಳೆ ಶಿಲಾಶಾಸನಗಳು",
            location = "Aihole, Bagalkot District",
            latitude = 16.0177, longitude = 75.8816,
            distanceKm = 22.4,
            era = "Chalukya – 634 CE onwards",
            dynastyEn = "Badami Chalukya Empire",
            dynastyKn = "ಬಾದಾಮಿ ಚಾಲುಕ್ಯ ಸಾಮ್ರಾಜ್ಯ",
            shortDescEn = "The 'Cradle of Indian Architecture' with 125 temples spanning 1,500 years of evolution.",
            shortDescKn = "1,500 ವರ್ಷಗಳ ವಾಸ್ತುಶಿಲ್ಪ ವಿಕಾಸವನ್ನು ಪ್ರದರ್ಶಿಸುವ 125 ದೇವಾಲಯಗಳ ತಾಣ.",
            fullDescEn = """Aihole (also spelled Aiholi) is called the "Cradle of Indian Temple Architecture" for good reason. This small village in Bagalkot district contains over 125 temples of varying styles, spanning a period from the 4th to the 12th century CE — making it an open-air museum of India's architectural evolution.

The Aihole inscription (634 CE), written by the court poet Ravikirti, is one of the most important epigraphic records in Indian history. Written in Sanskrit verse, it describes King Pulakesi II's victory over Emperor Harsha of Kanauj and provides the earliest known mention of the term "Kannada" for the language.

The temple complexes here show the transition from early rock-cut shrines to the elaborate structural temples that would later define South Indian architecture. The Durga Temple with its apsidal (semi-circular) form echoes Buddhist chaitya halls, showing how early Hindu architects experimented freely.

Aihole is where Indian architects solved the problem of building a stone roof — experiments visible in the different ceiling types across its 125 temples.""",
            fullDescKn = """ಐಹೊಳೆಯನ್ನು "ಭಾರತೀಯ ದೇವಾಲಯ ವಾಸ್ತುಶಿಲ್ಪದ ತೊಟ್ಟಿಲು" ಎಂದು ಕರೆಯಲಾಗುತ್ತದೆ. ಬಾಗಲಕೋಟೆ ಜಿಲ್ಲೆಯ ಈ ಸಣ್ಣ ಗ್ರಾಮದಲ್ಲಿ 125 ಕ್ಕೂ ಹೆಚ್ಚು ದೇವಾಲಯಗಳಿದ್ದು, 4 ರಿಂದ 12 ನೇ ಶತಮಾನ ಸಿಇ ವರೆಗಿನ ಕಾಲಘಟ್ಟಕ್ಕೆ ಸೇರಿವೆ.""",
            architecturalSignificance = "Experimental zone of early Indian temple architecture. Apsidal Durga Temple, flat-roofed Ladkhan Temple, and Meguti Jain Temple all in one cluster.",
            localLegend = "Villagers say that the god Brahma tested his blueprint for the universe here before creating the world, which is why so many architectural 'experiments' exist in one place.",
            hiddenFact = "🔍 Hidden Fact: The Aihole Inscription is so precise that historians used its astronomical calculations — recording a solar eclipse — to cross-verify modern astronomical software and confirm the exact date it was written: 634 CE!",
            tags = listOf("Chalukya", "Inscription", "Cradle of Architecture", "UNESCO Tentative"),
            category = SiteCategory.INSCRIPTION
        ),
        HeritageSite(
            id = "SITE_005",
            nameEn = "Shivagange Step Wells",
            nameKn = "ಶಿವಗಂಗೆ ಮೆಟ್ಟಿಲು ಬಾವಿಗಳು",
            location = "Shivagange Hill, Tumkur District",
            latitude = 13.1287, longitude = 77.4152,
            distanceKm = 18.9,
            era = "Ganga Dynasty – 9th Century CE",
            dynastyEn = "Western Ganga Dynasty",
            dynastyKn = "ಪಶ್ಚಿಮ ಗಂಗ ರಾಜವಂಶ",
            shortDescEn = "Ancient stepped water tanks carved into living rock atop a sacred hill still used by pilgrims.",
            shortDescKn = "ಪವಿತ್ರ ಬೆಟ್ಟದ ಮೇಲೆ ಕಲ್ಲಿನಲ್ಲಿ ಕೆತ್ತಿದ ಪ್ರಾಚೀನ ಮೆಟ್ಟಿಲು ಕೊಳಗಳು.",
            fullDescEn = """Shivagange, rising 1,368 metres above sea level near Dobbaspete, is not just a pilgrim destination — it is a marvel of ancient hydraulic engineering. The step wells and water tanks carved into the granite hillside by Ganga Dynasty engineers over 1,000 years ago still function today.

These stepped tanks (called 'Kalyani' in Kannada) were carved from the living rock — no mortar, no brickwork — just the patient chisel work of craftsmen who understood both devotion and hydrology. The tanks are arranged in a cascading fashion: rainwater collected at the summit flows through channels into progressively lower tanks, ensuring water availability at every level of the pilgrimage path.

The engineering innovation here is the overflow design — each tank's outlet is positioned such that it never overflows catastrophically but gently feeds the next tank, creating a self-regulating water management system centuries before modern hydraulics.

A large monolithic Nandi (sacred bull) carved at the summit faces east toward the sunrise — a 9th-century solar calendar that marks festival days by the shadow it casts on a carved grid below it.""",
            fullDescKn = """ಶಿವಗಂಗೆ ಕೇವಲ ಒಂದು ತೀರ್ಥಕ್ಷೇತ್ರವಲ್ಲ — ಇದು ಪ್ರಾಚೀನ ಜಲ ಎಂಜಿನಿಯರಿಂಗ್‌ನ ಒಂದು ಅದ್ಭುತ. ಗಂಗ ರಾಜವಂಶದ ಎಂಜಿನಿಯರ್‌ಗಳು 1,000 ವರ್ಷಗಳ ಹಿಂದೆ ಕಲ್ಲಿನ ಬೆಟ್ಟದ ಮೇಲೆ ಕೆತ್ತಿದ ಮೆಟ್ಟಿಲು ಕೊಳಗಳು ಇಂದಿಗೂ ಕಾರ್ಯನಿರ್ವಹಿಸುತ್ತಿವೆ.""",
            architecturalSignificance = "Rock-cut cascading step-well system — a pre-modern sustainable water management innovation still operational after 1,000 years.",
            localLegend = "Legend says the hill is Shiva's own meditation seat. The water in the summit tank is said to be fed not by rain alone but by a hidden underground spring blessed by Shiva himself — locals call it 'Ganga Jal' (Ganga's water).",
            hiddenFact = "🔍 Hidden Fact: The step-well system has 7 cascading tanks, and on the winter solstice, the setting sun aligns perfectly through a narrow rock cleft and illuminates the innermost shrine — a deliberate astronomical design by the Ganga Dynasty architects!",
            tags = listOf("Step Well", "Water Heritage", "Ganga Dynasty", "Pilgrimage", "Tumkur"),
            category = SiteCategory.STEP_WELL
        ),
        HeritageSite(
            id = "SITE_006",
            nameEn = "Badami Cave Temples",
            nameKn = "ಬಾದಾಮಿ ಗುಹಾ ದೇವಾಲಯಗಳು",
            location = "Badami, Bagalkot District",
            latitude = 15.9148, longitude = 75.6853,
            distanceKm = 35.1,
            era = "Chalukya – 6th–7th Century CE",
            dynastyEn = "Badami Chalukya Empire",
            dynastyKn = "ಬಾದಾಮಿ ಚಾಲುಕ್ಯ ಸಾಮ್ರಾಜ್ಯ",
            shortDescEn = "Four rock-cut cave temples carved into red sandstone cliffs — Hindu, Jain, and Shaiva all in one gorge.",
            shortDescKn = "ಕೆಂಪು ಮರಳುಕಲ್ಲಿನ ಬಂಡೆಗಳಲ್ಲಿ ಕೆತ್ತಿದ ನಾಲ್ಕು ಗುಹಾ ದೇವಾಲಯಗಳು.",
            fullDescEn = """The Badami Cave Temples are four rock-cut sanctuaries excavated from the soft red Badami sandstone cliffs that frame the Agastya Lake. Dating from the 6th to 7th century CE under the Badami Chalukya rulers, they represent a pivotal moment in Indian art — the transition from Gupta-era ideals to the fully developed Deccan style.

Cave 1 is dedicated to Shiva and contains the famous 18-armed Nataraja (dancing Shiva) — the largest Nataraja carving of its period in India. Cave 2 is dedicated to Vishnu and features a magnificent Trivikrama panel, where Vishnu is shown measuring the universe in three strides. Cave 3, the largest, is also Vaishnava and contains columns with ornate corbels and the earliest known Kannada inscription on its wall. Cave 4 is a Jain sanctuary with serene Tirthankara figures.

The technical achievement here is remarkable: all four caves are carved into living rock, descending into the cliff face, with every surface — ceiling, pillar, wall — carved with mythological narratives. The sculptors worked from top to bottom, removing stone systematically.

The site sits above the placid Agastya Lake, whose reflections at sunset turn the red sandstone cliffs to gold.""",
            fullDescKn = """ಬಾದಾಮಿ ಗುಹಾ ದೇವಾಲಯಗಳು ಅಗಸ್ತ್ಯ ಸರೋವರವನ್ನು ಚೌಕಟ್ಟು ಮಾಡುವ ಮೃದು ಕೆಂಪು ಬಾದಾಮಿ ಮರಳುಕಲ್ಲಿನ ಬಂಡೆಗಳಿಂದ ಕೊರೆದ ನಾಲ್ಕು ಬಂಡೆ-ಕೊರೆದ ಅಭಯಾರಣ್ಯಗಳಾಗಿವೆ.""",
            architecturalSignificance = "Earliest surviving rock-cut temples of the Deccan. Multi-religious (Hindu-Jain) complex. Contains earliest dated Kannada inscription (578 CE).",
            localLegend = "Locals say that the sage Agastya meditated at the lake for 100 years, and the water turned sacred. When the Chalukya king began cutting the caves, Agastya appeared and blessed the work — ensuring the sculptures would last forever.",
            hiddenFact = "🔍 Hidden Fact: Cave 3 contains the earliest known Kannada inscription, dated 578 CE, carved on a pillar. This makes Badami one of the earliest written records of the Kannada language — Karnataka's own linguistic birth certificate!",
            tags = listOf("Chalukya", "Rock-cut", "Cave Temple", "Jain", "Earliest Kannada Inscription"),
            category = SiteCategory.CAVE
        )
    )

    fun findById(id: String): HeritageSite? = allSites.find { it.id == id }

    fun getNearbySites(radiusKm: Double = 50.0): List<HeritageSite> =
        allSites.filter { it.distanceKm <= radiusKm }.sortedBy { it.distanceKm }
}
