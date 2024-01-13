$privateKeyPath = ".\private.pem"
$publicKeyPath = ".\public.pem"

if (!(Test-Path -Path $privateKeyPath -PathType Leaf) -or !(Test-Path -Path $publicKeyPath -PathType Leaf)) {
    Write-Error "Dateien private.pem oder public.pem nicht im aktuellen Verzeichnis gefunden. Bitte kopieren Sie private.pem und public.pem im selben Verzeichnis wie dieses Skript."
    exit
}

$privateKeyContent = Get-Content $privateKeyPath -Raw
$publicKeyContent = Get-Content $publicKeyPath -Raw

$base64PrivateKey = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($privateKeyContent))
$base64PublicKey = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($publicKeyContent))

[System.Environment]::SetEnvironmentVariable("HEROTOZERO_APP_PRIVATE_KEY", $base64PrivateKey, [System.EnvironmentVariableTarget]::User)
[System.Environment]::SetEnvironmentVariable("HEROTOZERO_APP_PUBLIC_KEY", $base64PublicKey, [System.EnvironmentVariableTarget]::User)

Write-Host "Umgebungsvariablen wurden erfolgreich gesetzt."
