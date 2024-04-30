# Introduction

>First off, thank you for considering contributing to Restalo. It's people like you that will allow Restalo to thrive!

### Why you should read our guidelines.

>Following these guidelines helps to communicate that you respect the time of the developers managing and developing this open source project. In return, they should reciprocate that respect in addressing your issue, assessing changes, and helping you finalize your pull requests.

### How you can contribute

> Restalo is an open source project and we love to receive contributions from our community — you! There are many ways to contribute, from writing tutorials or blog posts, improving the documentation, submitting bug reports and feature requests or writing code which can be incorporated into Restalo itself.


### Unwanted Contributions

> Please don't create issues or PRs to fix small cosmetic or formatting errors. Such issues will not be accepted by our team.

# Ground Rules

> Responsibilities
> * Create issues for any major changes and enhancements that you wish to make. Discuss things transparently and get community feedback and a maintainer's approval before advancing.
> * Keep feature versions as small as possible, preferably one new feature per version.
> * Be welcoming to newcomers and encourage diverse new contributors from all backgrounds.



### For Beginners

> Working on your first Pull Request? You can learn how from this *free* series, [How to Contribute to an Open Source Project on GitHub](https://egghead.io/series/how-to-contribute-to-an-open-source-project-on-github).


>At this point, you're ready to make your changes! Feel free to ask for help; everyone is a beginner at first :)
>
>If a maintainer asks you to "rebase" your PR, they're saying that a lot of code has changed, and that you need to update your branch so it's easier to merge.

# Getting started

# How to report a bug

> Any security issues should be submitted directly to rudy.saal.1@ulaval.ca or edwin.lemelin.1@ulaval.ca. 
> In order to determine whether you are dealing with a security issue, ask yourself these two questions:
> * Can I access something that's not mine, or something I shouldn't have access to?
> * Can I disable something for other people?
>
> If the answer to either of those two questions are "yes", then you're probably dealing with a security issue. Note that even if you answer "no" to both questions, you may still be dealing with a security issue, so if you're unsure, just email us at rudy.saal.1@ulaval.ca or edwin.lemelin.1@ulaval.ca.

>Before filing an issue, make sure to verify that it has not already been adressed as an issue.

> When filing an issue, make sure to answer these three questions:
>
> 1. What did you do?
> 2. What did you expect to see?
> 3. What did you see instead?


# How to suggest a feature or enhancement

> Restalo hopes to centralize creating reservations to ease the load on restaurants and to improve the experience for customers,
while avoiding scheduling issues and other common errors
>
> Currently, we are mainly looking for bug fixes and performance improvements to our software, but we are also open to potential new features.

> If you find yourself wishing for a feature that doesn't exist in Restalo, you are probably not alone. There are bound to be others out there with similar needs. Open an issue on our issues list on GitHub which describes the feature you would like to see, why you need it, and how it should work.

## Submitting Pull requests

#### **Did you write a patch that fixes a bug?**

> Open a new GitHub pull request with the patch.

> Ensure the PR description clearly describes the problem and solution. Include the relevant issue number if applicable.

#### **Did you fix whitespace, format code, or make a purely cosmetic patch?**

>Changes that are simply cosmetic in nature and do not add anything substantial to the stability, functionality, or testability of Restalo will generally not be accepted.

#### **Do you intend to add a new feature or change an existing one?**

> Suggest your change as an issue with the label "feature".
> Do not start writing code until positive feedback and approval by a maintainer is received on the issue.


>For something that is bigger than a one or two line fix:
>1. Create your own fork of the code
>2. Do the changes in your fork
>3. Send a pull request


# Conventions


### Commit Naming

We follow the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/#summary) standard for our commit naming convention. Specifically, our commit messages are structured as follows:

    <type>[optional scope]: <description>

Here are the types we use and their purposes:

- **`fix`**: To fix a bug in the code.
- **`feat`**: To introduce a new feature into the code.
- **`docs`**: To add documentation.
- **`refactor`**: When the code is restructured/refactored.
- **`test`**: To add tests to the code.
- **`del`**: When deleting files.
- **`misc`**: If none of the other types apply.

The optional `scope` attribute refers to a word that provides additional contextual information. It is enclosed in parentheses and should describe a section of the code.

Finally, the description is a short phrase describing the reason for the commit. It usually starts with an infinitive verb, does not begin with a capital letter, and does not end with a period.

Here are some examples taken from Conventional Commits:

    feat(lang): add Polish language
    fix: prevent racing of requests
    docs: correct spelling of CHANGELOG

### Branching Strategy

This session, we employ a branching strategy inspired by [GitHub Flow](https://githubflow.github.io/) and [GitLab Flow](https://about.gitlab.com/topics/version-control/what-is-gitlab-flow/). Here are the details:

1. Our repository contains two main branches: `main` and `production`. The `main` branch is our working branch. From this branch, we create other branches to implement new features or fix bugs. Once the code on `main` is ready to be deployed, it is integrated into the `production` branch, which contains the official, tested, and functional code of our application.

2. Our main branch is `production`, as it contains the code and documentation ready for delivery. Additionally, it holds the tags for the delivery of different parts of the project.

3. Like the GitHub Flow strategy, we choose to create a new branch from `main` each time we work on something new. Thus, a new branch will be created for each of the issues currently in progress. For more details on branch naming, see [Branch Naming](#branch-naming).

4. Once a developer has completed the issue assigned to them, they can create a pull request to merge their code into the `main` branch (the workspace). We use the merge command rather than rebase for any branch merging in our repository. As our team is relatively small, we consider this option to be simpler.

### Branch Naming

Depending on the situation, a branch may be created to develop a new feature, fix a bug in the workspace, fix a bug in the production environment, etc. Thus, to ensure consistency within the team, we adopt the following structure to name our branches:

    <prefix>/<issue-number>/<description>

The available prefixes are:

1. **`feature`**: When adding a feature to the code.
2. **`bugfix`**: When there is a bug to fix in the workspace (main branch).
3. **`hotfix`**: In case there is a bug to be fixed very quickly on a version of the code in production (production branch).
4. **`docs`**: When additions are documentation.
5. **`misc`**: If none of the other types apply.

The `issue-number` parameter corresponds to the number of the issue associated with the branch.

The branch description should consist of a few words that succinctly describe the work done on this branch. The description is composed of lowercase alphanumeric characters, and each word is separated by a hyphen.

Here are some examples following the conventions mentioned above:

    feature/12/improved-ui-login
    bugfix/8/fix-api-response-time
    docs/2/add-exercices-tp1
# Code review process


> Once a Pull Request has been created, one of our core team members will submit a review within a week. If a response to the review is not submitted within two weeks of this, the pull request will normally be closed due to inactivity.


# Testing
>To test locally, run 
 ```
mvn test
```
>On github, our tests run automatically with actions, so make sure they all pass when creating a PR.
>


This Contributing guideline is adapted from the [Nayafia-code_of_conduct](https://www.contributor-covenant.org/version/2/1/code_of_conduct.html),
and certain guidelines were inspired by Ruby on Rails' [Rails-code_of_conduct](https://github.com/rails/rails/blob/main/CONTRIBUTING.md)