$rsa = New-Object System.Security.Cryptography.RSACryptoServiceProvider(2048)
$privateKey = $rsa.ToXmlString($true)
$publicKey = $rsa.ToXmlString($false)

$base64PrivateKey = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($privateKey))
$base64PublicKey = [Convert]::ToBase64String([System.Text.Encoding]::UTF8.GetBytes($publicKey))

[System.Environment]::SetEnvironmentVariable("HEROTOZERO_APP_PRIVATE_KEY", $base64PrivateKey, [System.EnvironmentVariableTarget]::Machine)
[System.Environment]::SetEnvironmentVariable("HEROTOZERO_APP_PUBLIC_KEY", $base64PublicKey, [System.EnvironmentVariableTarget]::Machine)

Write-Host "RSA-Schlüsselpaar generiert und Umgebungsvariablen erfolgreich gesetzt."