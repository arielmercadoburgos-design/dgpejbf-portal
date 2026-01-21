@RestController
@RequestMapping("/api")
public class PejRaActualResource {

    private final PejRaActualService pejRaActualService;

    public PejRaActualResource(PejRaActualService pejRaActualService) {
        this.pejRaActualService = pejRaActualService;
    }

    @GetMapping("/pej-ra-actual/directivos/{ruc}")
    public List<PejRaActualDirectivoDTO> getDirectivos(
        @PathVariable Integer ruc
    ) {
        return pejRaActualService.findDirectivosByRuc(ruc);
    }
}
