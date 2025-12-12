# API examples — Auth & Temperature Conversion

This document shows example `curl` commands to register/login, obtain a JWT `authToken`, call the public temperature conversion endpoint, and call protected endpoints using the Bearer token.

Base URL: `http://localhost:8080`

1) Register (create user)

```bash
curl -s -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"tester","email":"tester@example.com","password":"secret123"}'
```

2) Login (obtain `authToken`)

```bash
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"tester@example.com","password":"secret123"}'

# Example response:
# {"status":"success","message":"Login berhasil","data":{"authToken":"<JWT>"}}
```

3) Extract token with `jq` (Linux/macOS/Git Bash / PowerShell with jq installed)

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"tester@example.com","password":"secret123"}' | jq -r '.data.authToken')
echo $TOKEN
```

4) Call public temperature conversion (no token required)

```bash
curl -s -X POST http://localhost:8080/api/temperature/convert \
  -H "Content-Type: application/json" \
  -d '{"temperature":25,"unit":"CELSIUS"}'

# Response example contains converted values (celsius, fahrenheit, kelvin, reamur)
```

5) Call protected endpoint with Bearer token

```bash
curl -s -H "Authorization: Bearer $TOKEN" \
  http://localhost:8080/api/todos

# Protected endpoints return 401/403 without a valid token.
```

Notes
- The test `tools/TestHttpClient.java` included in the repo demonstrates the same flow programmatically.
- Keep secrets and JWT signing keys out of source control in production; use environment variables or a secrets manager.
