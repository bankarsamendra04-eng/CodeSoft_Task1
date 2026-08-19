# CareerGap AI Build Plan

## Dependencies

- No package manager dependencies for the MVP.
- Vanilla HTML, CSS, and JavaScript keep the demo portable and easy to deploy.
- Optional future integrations can add FastAPI, Gemini, PyMuPDF, SQLite, and GitHub REST API without changing the dashboard flow.

## Build steps

1. Replace the Swing-only entry surface with a responsive CareerGap AI single-page experience.
2. Add a landing/upload workspace with role selection, sample resume loading, and local PDF/text validation.
3. Add deterministic demo analysis data with transparent readiness scoring.
4. Add dashboard navigation for skills, roadmap, projects, and interview preparation.
5. Add responsive styling, accessible controls, and lightweight chart rendering with CSS/SVG only.
6. Run a local static server and verify the primary user flow.

## Blueprint sections

- `initialize`: ensure Python 3 is available; no third-party install is required.
- `test`: start a static server and run browser/smoke checks.
- `build`: no compilation step; validate JavaScript syntax and static asset presence.
- `startup`: serve the repository root with `python3 -m http.server 4173`.

## Pre-commit hooks

No `.pre-commit-config.yaml` exists in the repository, so no hook setup is required.
