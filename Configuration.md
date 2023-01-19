# Configuration

## API

File: `(TablistManagerAPI/tablistConfig.yml)`

| Field                            | Description                                                                                                                                                                                        |
|----------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| tablistPerWorld: **boolean**     | Define whether the player list should be per world (Player A in world W should not se Player B in world X)                                                                                         | 
| useRealLatency: **boolean**      | Sets if the tablist is composed by the real latency bars or set by the faked value of the 'defaultLatency' field                                                                                   |
| defaultLatency: **enum**         | Amount of latency bars showed in the tablist if the 'useRealLatency: false'. **Valid values:** `ZERO, ONE, TWO, THREE, FOUR, FIVE`                                                                 |
| fillWithFakePlayers: **boolean** | Fill the tablist with fake players.                                                                                                                                                                |
| fillUntil: **int**               | The max num of fake players to show on the tablist. e.g. If set to 21, and there are 5 players connected then it will show 16 faked slots. If 21 players or more are connected then none is shown. |

## Plugin

File: `(TablistManager/config.yml)`

| Field                                | Description                                                                     |
|--------------------------------------|---------------------------------------------------------------------------------|
| headers: **key-pair of string list** | Defines the per-world tablist header shown to the player of the specified world |
| footers: **key-pair of string list** | Defines the per-world tablist footer shown to the player of the specified world |
| defaultHeader: **string list**       | Defines the default Header for the worlds where a specific header isn't set.    |
| defaultFooter: **string list**       | The same as above but of Footer                                                 |

Example of plugin configuration:

```yml
footers:
  hola:
    - '&ahola'
    - 'adiós'
  adios:
    - 'adios'
    - 'hola'
headers:
  world:
    - '1'
  world_nether:
    - '2'
defaultHeader:
  - '&cTablist Manager'
defaultFooter:
  - '&aBy &lErnestoRB'
```

- The world *hola* will have a tablist header with 'hola\nadiós' text of color green. And a footer of '1'.
- The world *adios* will show "adios\nhola" at the header. 'By ErnestoRB' in green at the footer.
- The world *world_nether* will show '2' at the footer. And 'Tablist Manager' in red at the header.
- For all other worlds the defaultHeader and defaultFooter are shown.