#!/usr/bin/env python
"""
Python port of org.oclc.catcher.Catcher.
Prints:
  • Catcher web‑service version
  • List of collections
  • Collection field/attribute configuration
  • Controlled‑vocabulary terms for one field
  • CONTENTdm upload‑module (HTTP Transfer) version
"""
from zeep import Client, helpers
from zeep.exceptions import Fault

# ---------- CONSTANTS (replace or externalize for security) ----------
CDMURL       = "http://server17480.contentdm.oclc.org:8888"
CDMUSERNAME  = "SarahLong"
CDMPASSWORD  = "ep9riseSkinwalker"
CDMLICENSE   = "R7WWM-LE5RB-HPYRT-RZJ53"
CDMALIAS     = "/leirner"             # collection alias
CDMCVFIELD   = "rights"               # controlled‑vocabulary field
# WSDL endpoint; adjust if your server’s path differs
WSDL_URL = f"https://worldcat.org/webservices/contentdm/catcher/6.0/CatcherService.wsdl"
# ---------------------------------------------------------------------

def main() -> None:
    try:
        client = Client(wsdl=WSDL_URL)          # connect to SOAP service
        svc    = client.service                 # shorthand

        print("Current web service version\n---------------------------")
        print(svc.getWSVersion())

        print("\n\nList of collections\n------------------")
        print(svc.getCONTENTdmCatalog(
            CDMURL, CDMUSERNAME, CDMPASSWORD, CDMLICENSE))

        print("\n\nCollection fields/attributes\n----------------------------")
        print(svc.getCONTENTdmCollectionConfig(
            CDMURL, CDMUSERNAME, CDMPASSWORD, CDMLICENSE, CDMALIAS))

        print("\n\nControlled vocabulary terms\n----------------------------")
        print(svc.getCONTENTdmControlledVocabTerms(
            CDMURL, CDMUSERNAME, CDMPASSWORD,
            CDMLICENSE, CDMALIAS, CDMCVFIELD))

        print("\n\nCONTENTdm upload module version\n-------------------------------")
        print(svc.getCONTENTdmHTTPTransferVersion(CDMURL, CDMUSERNAME, CDMPASSWORD))

        print("\n\nDONE")

    except Fault as fault:
        # SOAP‑level error (bad credentials, wrong params, etc.)
        print("SOAP Fault:", fault)
    except Exception as exc:
        print("Unhandled error:", exc)

if __name__ == "__main__":
    main()
