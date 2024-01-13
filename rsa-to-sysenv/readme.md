# Anleitung zum Ausführen der Skripte (Windows)

## GenerateAndSetRSAEnvVariables.ps1
Wenn sie die public.pem und private.pem automatisch generieren lassen wollen und als Umgebungsvariablen setzen lassen wollen nutzen sie dieses Skript.

- Öffnen Sie Powershell als Administrator
- Navigieren Sie zum Verzeichnis in dem das Skript liegt
- führen Sie ".\GenerateAndSetKeysEnv.ps1" aus


## SetRSAEnvVariables.ps1
Wenn Sie selbst ein public.pem und private.pem RSA-Schlüssenpaar erzeugt haben führen Sie dieses Skript aus.

- Kopieren Sie ihre public.pem und private.pem in das selbe Verzeichnis wie dieses Skript
- Öffnen Sie Powershell als Administrator
- Navigieren Sie zum Verzeichnis in dem das Skript liegt
- führen Sie ".\GenerateAndSetKeysEnv.ps1" aus



## Überprüfen der System-Umgebungsvariablen

Führen Sie folgendes in einer PowerShell Umgebung aus um zu überprüfen ob die Umgebungsvariablen richtig gesetzt wurden:

´´´ [System.Environment]::GetEnvironmentVariable("HEROTOZERO_APP_PRIVATE_KEY", [System.EnvironmentVariableTarget]::Machine)
[System.Environment]::GetEnvironmentVariable("HEROTOZERO_APP_PUBLIC_KEY", [System.EnvironmentVariableTarget]::Machine)
´´´