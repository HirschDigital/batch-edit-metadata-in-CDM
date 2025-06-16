from zeep import Client

wsdl = 'http://server17480.contentdm.oclc.org/cgi-bin/cdm4/catcher/catcher.dll?WSDL'

client = Client(wsdl=wsdl)

try:
    session = client.service.Login('SarahLong', 'ep9riseSkinwalker')
    print("Login successful. Session ID:", session)
except Exception as e:
    print("Login failed:", e)

for service in client.wsdl.services.values():
    for port in service.ports.values():
        operations = port.binding._operations.values()
        for op in operations:
            print(f"Operation: {op.name}")
