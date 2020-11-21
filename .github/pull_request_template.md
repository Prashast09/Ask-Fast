Please, go through these steps when you submit a PR:

1. You have done your changes in a separate branch. Branches MUST have descriptive names that start with either the `fix/{CLICKUP-TICKET}` or `feature/{CLICKUP-TICKET}` prefixes. Good examples are: `fix/CU-ai4ft/signin-issue` or `feature/CU-pe9if/new-emoji-strip`.

2. If you have added any new dependency, you’ve tested it with a release APK for proguard failures.

3. Give a descriptive title to your PR. It SHOULD include the CLICKUP ticket number (in caps) at the starting of the PR in square brackets. For example:
`[CU-ai4ft] Grid view image optimizations`. If there is no CLICKUP ticket and this is a developer story or fix, use `[NO-CARD]` in that case.

**** **REMOVE THE ABOVE SECTION BEFORE SUBMITTING THE PR** ****


### Clickup Ticket
https://app.clickup.com/t/{TICKET_NUMBER}

### Brief Description
{Add a brief description of your changes here}

### Checklist
Please delete options that are not relevant.

- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Backend changes for this PR is deployed